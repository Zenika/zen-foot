import fr.boillodmanuel.restx.gae.dao.token.Token;
import fr.boillodmanuel.restx.gae.dao.token.TokenService;


public class JUnitTest {
	
	public static boolean tokenService(){
		Token token = Token.generateToken();
		Token token2 = new Token(token);
		
		
		
		TokenService tokenService= new TokenService();
		tokenService.addToken(token);
		
		return tokenService.isValid(token2);
		
		
	}
	
	
	public static void main(String[] args) {
		System.out.println("Test1 : "+(tokenService()?"Test ok":"Not OK"));
	}

}
