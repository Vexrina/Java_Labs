public class Palindrome {
    public static void main(String[] args){
        for(int i = 0; i < args.length; i++){
            String s = args[i];
            String reverse = reverseString(s);
            if(isPalindrome(s))System.out.print(" yes");
            else System.out.print(" no");
        }
    }
    public static String reverseString(String s){
        String reversed = "";
        for(int i = s.length()-1; i >= 0; i--){
            reversed += s.charAt(i);
        }
        return reversed;
    }
    public static boolean isPalindrome(String s){
        return s.equals(reverseString(s));
    }
}
