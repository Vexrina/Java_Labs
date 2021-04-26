public class Primes {
    public static void main(String[] args){
        int a=2;
        int n=100;
        while (a<n){
            if(PrimeNumer(a)){
                System.out.print(a);
                System.out.print(' ');
            }
            a++;
        };
        }
    public static boolean PrimeNumer(int a) {
        if (a == 2 || a == 3) {
            return true;
        }
        if (a <= 1 || a % 2 == 0) {
            return false;
        }
        if (a > 3) {
            for (int i = 3; i <= Math.sqrt(a); i += 2) {
                if (a % i == 0) return false;
            }
            return true;
        }
        return false;
}
}
