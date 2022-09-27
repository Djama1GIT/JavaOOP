// finds palindromes
class Palindrome {
  public static void main(String[] args) {
    Boolean bool = true;
    for (String arg : args) {
      if (!isPalindrome(arg)) {
        bool = false;
      }
    }

    System.out.println(bool);
  }

  // reverses string
  public static String reverseString(String str) {
    String result = "";

    int length = str.length() - 1;

    for (int i = length; i >= 0; i--) {
      result += str.charAt(i);
    }

    return result;
  }

  // returns true if string is palindrome
  public static boolean isPalindrome(String str) {
    return str.equals(reverseString(str));
  }
}
