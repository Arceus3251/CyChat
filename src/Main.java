public class Main {
    public static void main(String[] args){
        //This is disgusting, but hey it works.
        //Will Contain Signing Up for our service as well, I marked in a comment where that is in "LoginPage.java" <3

        //Adding logic for retry attempts, adjust as needed.

        //for(int i = 3; i>-1; i--){
            LoginPage loginPage = new LoginPage();
            String[] input = loginPage.getInfo();
            while(input[0] == null){
                input = loginPage.getInfo();
            }
            //input should now be an array containing User Name as a String (input[0]) and Password as a Character Array (input[1]). We need to authenticate this.
            //if(login is valid){
                //break;
            //}
            //if(i==0){
                //JOptionPane.showMessageDialog(null, "Too many invalid attempts.");
                //System.exit(1);
            //}
            //JOptionPane.showMessageDialog(null, "Invalid Username and/or Password, you have "+i+" attempts remaining");
        //}
        //From confirming authentication, we should send a packet out to the server saying we are alive.
        //From here, open contact List?
    }
}
