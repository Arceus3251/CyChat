public class Main {
    public static void main(String[] args){
        //This is disgusting, but hey it works.
        LoginPage loginPage = new LoginPage();
        String[] input = loginPage.getInfo();
        while(input[0] == null){
            input = loginPage.getInfo();
        }
        //input should now be an array containing User Name as a String and Password as a Character Array. We need to authenticate this.
        //From confirming authentication, we should send a packet out to the server saying we are alive.
        //From here, open contact List?
    }
}
