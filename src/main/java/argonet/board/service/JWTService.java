package argonet.board.service;

import argonet.board.entity.Member;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTService {

    @Value("${board.key}")
    private String baseKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Key createKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(baseKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    public String createJwt(Member member) throws Exception {
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", member.getId());
        claims.put("account", member.getAccount());

        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60 * 1);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claims)
                .setExpiration(expireTime)
                .signWith(createKey(), signatureAlgorithm);

        String result = builder.compact();
        return result;
    }

    public Boolean checkJwt(String jwt) throws Exception {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(baseKey))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}