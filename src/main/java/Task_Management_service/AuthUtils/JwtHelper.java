package Task_Management_service.AuthUtils;


import Task_Management_service.constant.ApiErrorCodes;
import Task_Management_service.entity.UserEntity;
import Task_Management_service.exception.NoSuchElementFoundException;
import Task_Management_service.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Setter
@Component
public class JwtHelper {


    @Autowired
    private UserRepo userRepo;

    @Value("${jwt.tokenValidityInSeconds}")
    public long JWT_TOKEN_VALIDITY ;

    private final String secret = "ABCDEFGHIJfVIVEKghklKLMNNDHDNDO01234persisjpafkfkjydcjsdcknsjdt5PQRSUVWXYZabcdemnouvwxyz664565665178-_"; // secret code
    byte[] secretKeyBytes = secret.getBytes();

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKeyBytes)).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {                                      // checking expire
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        System.out.println(userDetails.getUsername());
        Optional<UserEntity> optionalUserEntity = userRepo.findByMobileNo(userDetails.getUsername());
        if(optionalUserEntity.isEmpty()){
            throw new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage());
        }
        UserEntity user = optionalUserEntity.get();
        claims.put("email",user.getEmail());
        claims.put("mobileNo",user.getMobileNo());
        claims.put("username",user.getUsername());
        claims.put("id", user.getId());
        return doGenerateToken(claims, user.getMobileNo());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()),SignatureAlgorithm.HS512).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean validateOnlyToken(String token) {
        return !isTokenExpired(token);
    }

}
