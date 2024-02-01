/**
* Assignment: SDEV200_M03_Assignment2_Ex13_15
* File: TestRationalClass.java
* Version: 1.0
* Date: 1/31/2024
* Author: Tomomi Hobara
* Description: This program defines the Rational class and methods that perform addition, subtraction, multiplication, and division.
               Then, it receives user input for two rational numbers and prints the calculation results.             
* Variables: 
    - r1, 2, 3: Rational class objects
    - numerator: BigInteger, data field for the Rational object
    - denominator: BigInteger, data field for the Rational object
    - gcd: BigInteger, the great common divisor for the two rational numbers

* Steps:
    1. Define the Rational class with constructors
    2. Define accessor and mutator methods
    3. Override methods of the superclass and the interface (Object, Comparable, and Number)
    4. Test the constructors and methods by creating Rational objects and performing calculations
*/

import java.math.BigInteger;
import java.util.Scanner;

/** Create Rational instances from user input and print the calculation results */
public class TestRationalClass {
  public static void main(String[] args) {
    //Create a Scanner
    Scanner input = new Scanner(System.in);

    // Receive input for two rational numbers
    System.out.println("Enter the first rational number. For example, 3 454: ");
    BigInteger n1 = input.nextBigInteger();
    BigInteger d1 = input.nextBigInteger();
 
    System.out.println("Enter the second rational number. For example, 7 2389: ");
    BigInteger n2 = input.nextBigInteger();
    BigInteger d2 = input.nextBigInteger();

    // Create instances
    Rational r1 = new Rational(n1, d1);
    Rational r2 = new Rational(n2, d2);

    // Display results
    System.out.println(r1 + " + " + r2 + " = " + r1.add(r2));
    System.out.println(r1 + " - " + r2 + " = " + r1.subtract(r2));
    System.out.println(r1 + " * " + r2 + " = " + r1.multiply(r2));
    System.out.println(r1 + " / " + r2 + " = " + r1.divide(r2));
    System.out.println(r2 + " is " + r2.doubleValue());

    // Close the Scanner
    input.close();
  }
}

// /** Test the Rational class to see if changing from long to BigInteger solves calculation errors for big numbers. */
// public class TestRationalClass {
//   public static void main(String[] args) {
//     Rational r1 = new Rational(new BigInteger("1"), new BigInteger("123456789"));   // The original code is on p.531
//     Rational r2 = new Rational(new BigInteger("1"), new BigInteger("123456789"));
//     Rational r3 = new Rational(new BigInteger("1"), new BigInteger("123456789"));
//     System.out.println("r1 * r2 * r3 is " +
//       r1.multiply(r2.multiply(r3)));
//   }
// }

/** Test Rational class that was rewritten as BigInteger. The original code is in the textbook, p.527*/
// public class TestRationalClass {
//   /** Main method */
//   public static void main(String[] args) {
//     // Create and initialize two rational numbers r1 and r2.
//     Rational r1 = new Rational(new BigInteger("4"), new BigInteger("2"));
//     Rational r2 = new Rational(new BigInteger("2"), new BigInteger("3"));

//     // Display results
//     System.out.println(r1 + " + " + r2 + " = " + r1.add(r2));
//     System.out.println(r1 + " - " + r2 + " = " + r1.subtract(r2));
//     System.out.println(r1 + " * " + r2 + " = " + r1.multiply(r2));
//     System.out.println(r1 + " / " + r2 + " = " + r1.divide(r2));
//     System.out.println(r2 + " is " + r2.doubleValue());
//   }
// }


/** Re-write the Rational class with BigInteger type */
class Rational extends Number implements Comparable<Rational> {
  // Data fields for numerator and denominator with default values in BigInteger
  private BigInteger numerator = new BigInteger("0");
  private BigInteger denominator = new BigInteger("1");

  /** Construct a rational with default properties */
  public Rational() {
    this(new BigInteger("0") , new BigInteger("1"));
  }


  /** Construct a rational with specified numerator and denominator */
  public Rational(BigInteger numerator, BigInteger denominator) {
    BigInteger gcd = gcd(numerator, denominator);
    this.numerator = ((denominator.compareTo(new BigInteger("0")) > 0) ? new BigInteger("1") : new BigInteger("-1")).multiply(numerator).divide(gcd);
    this.denominator = denominator.abs().divide(gcd);
  }

  /** Find the GCD of two numbers */
  private static BigInteger gcd(BigInteger n, BigInteger d) {
    BigInteger n1 = n.abs();
    BigInteger n2 = d.abs();
    BigInteger gcd = new BigInteger("1");
    
    // Check if both n1 and n2 are divisible by k. If true, update gcd with k. Repeat until k is bigger than n1 or n2.
    for (BigInteger k = new BigInteger("1"); k.compareTo(n1) <= 0 && k.compareTo(n2) <= 0; k = k.add(new BigInteger("1"))) {
      if (n1.remainder(k).equals(new BigInteger("0")) && n2.remainder(k).equals(new BigInteger("0"))) {
        gcd = k;
      }
    }

    return gcd;
  }

  /** Return numerator */
  public BigInteger getNumerator() {
    return numerator;
  }

  /** Return denominator */
  public BigInteger getDenominator() {
    return denominator;
  }

  /** Add a rational number to this rational.
      a/b + c/d = (ad + bc)/(bd), e.g., 2/3 + 3/4 = (2*4 + 3*3)/(3*4) = 17/12 */
  
  public Rational add(Rational secondRational) {
    BigInteger n = numerator.multiply(secondRational.getDenominator()).add(denominator.multiply(secondRational.getNumerator()));
    BigInteger d = denominator.multiply(secondRational.getDenominator());
    return new Rational(n, d);
  }

  /** Subtract a rational number from this rational.
      a/b - c/d = (ad - bc)/(bd), e.g., 2/3 - 3/4 = (2*4 - 3*3)/(3*4) = -1/12 */
  public Rational subtract(Rational secondRational) {
    BigInteger n = numerator.multiply(secondRational.getDenominator()).subtract(denominator.multiply(secondRational.getNumerator()));
    BigInteger d = denominator.multiply(secondRational.getDenominator());
    return new Rational(n, d);
  }

  /** Multiply a rational number to this rational. 
      a/b * c/d = (ac)/(bd), e.g., 2/3 * 3/4 = (2*3)/(3*4) = 6/12 = 1/2 */
  public Rational multiply(Rational secondRational) {
    BigInteger n = numerator.multiply(secondRational.getNumerator());
    BigInteger d = denominator.multiply(secondRational.getDenominator());
    return new Rational(n, d);
  }

  /** Divide a rational number from this rational. 
      (a/b) / (c/d) = (ad)/(bc), e.g.,  (2/3) / (3/4) = (2*4) / (3*3) = 8/9 */
  public Rational divide(Rational secondRational) {
    BigInteger n = numerator.multiply(secondRational.getDenominator());
    BigInteger d = denominator.multiply(secondRational.numerator);
    return new Rational(n, d);
  }

  /** Override the toString method in the Object class and print the Rational object. */
  @Override  
  public String toString() {
    if (denominator.equals(new BigInteger("1")))
      return numerator + "";
    else
      return numerator + "/" + denominator;
  }

  /** Override the equals method in the Object class. Check if the rational number is equal to 
  * the second rational number by using the subtract method. */
  @Override 
  public boolean equals(Object other) {
    if ((this.subtract((Rational)(other))).getNumerator().equals(new BigInteger("0")))
      return true;
    else
      return false;
  }

  /** Implement the compareTo method in the Comparable class. Check if the rational number is
   *  greater than/less than/equal to the second rational number by using the subtract method. */
  @Override 
  public int compareTo(Rational o) {
    if (this.subtract(o).getNumerator().compareTo(new BigInteger("0")) > 0)
      return 1;
    else if (this.subtract(o).getNumerator().compareTo(new BigInteger("0")) < 0)
      return -1;
    else
      return 0;
  }

  @Override // Implement the doubleValue method in Number 
  public double doubleValue() {
    return numerator.doubleValue() / denominator.doubleValue();
  }

  @Override // Implement the abstract intValue method in Number 
  public int intValue() {
    return (int)doubleValue();
  }

  @Override // Implement the abstract floatValue method in Number 
  public float floatValue() {
    return (float)doubleValue();
  }


  @Override // Implement the abstract longValue method in Number
  public long longValue() {
    return (long)doubleValue();
  }

}