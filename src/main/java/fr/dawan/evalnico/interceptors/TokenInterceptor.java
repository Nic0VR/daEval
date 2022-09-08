package fr.dawan.evalnico.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fr.dawan.evalnico.exceptions.TokenException;
import fr.dawan.evalnico.tools.JwtTokenUtil;
import fr.dawan.evalnico.tools.TokenSaver;
import io.jsonwebtoken.Claims;



@Component
public class TokenInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(!request.getMethod().equals("OPTIONS")) {//on ne doit pas interception les req : OPTIONS
			
			if(!request.getRequestURI().equals("/login")
				&& !request.getRequestURI().equals("/reset-password")
				&& !request.getRequestURI().equals("/change-password")
					) {//ni les req /login
//				récupérer l'entête Authorization (bearer TOKENNNNN)
				String headerAuth = request.getHeader("Authorization");
				// si aucun jeton ou non conforme <7 => exception jeton absent ou non valide
				if(headerAuth==null || headerAuth.trim().equals("") || headerAuth.length()<7)
					throw new TokenException("Error : Invalid token !");
				
				//	découpe pour extraire le jeton
				String token = headerAuth.substring(7);
				//	valider le jeton en vérifiant qu'il n'a pas expiré
				if(jwtTokenUtil.isTokenExpired(token))
					throw new TokenException("Error : Expired token !");
				
				//vérifier que c'est un jeton qui a été stocké dans la map (TokenSaver)
				String email = jwtTokenUtil.getUsernameFromToken(token);
				if(!TokenSaver.tokensByEmail.containsKey(email) 
						|| !TokenSaver.tokensByEmail.get(email).equals(token))
					throw new TokenException("Error : Unknown token !");
				
				
				//Autre : ....
				Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);

				if(!claims.get("user_role").toString().contentEquals("ADMINISTRATEUR")) {
					throw new TokenException("Error : Unauthorized :");
				}
			}
		}
		
		
		return true;
	}

	
}
