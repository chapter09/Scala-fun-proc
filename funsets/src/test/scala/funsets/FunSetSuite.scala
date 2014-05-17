package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import junit.framework.TestSuite

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect returns the intersection") {
    new TestSets {
      val u_s = union(s1, s2)
      val s = intersect(s1, u_s)
      assert(contains(s, 1), "1 in intersection of s1 and s2")
      assert(!contains(s, 2), "2 not in intersection of s1 and s2")
    }
  }

  test("diff returns the difference of tow sets") {
    new TestSets {
      val s = diff(s1, s2)
      assert(contains(s, 1), "1 in diff set of s1 and s2")
      assert(!contains(s, 2), "2 in diff set of s1 and s2")
    }
  }

  test("filter return subset of the given set with constraints") {
    new TestSets {
      val func = (x: Int) => x % 2 == 0
      val filter_1 = filter(s2, func)
      val filter_2 = filter(s3, func)
      assert(contains(filter_1, 2), "2 matches the func")
      assert(!contains(filter_2, 3), "3 not matches the func")
    }
  }

  test("forall return whether all bounded integers matches") {
    new TestSets {
      val temp_s = union(s1, s2)
      val s = union(temp_s, s3)
      assert(forall(s, (i: Int) => i > 0), "all the elements are above 0")
      assert(!forall(s, (i: Int) => i < 0), "all the elements are not below 0")
    }
  }

  test("exists return whether there exists a bounded integer matched") {
    new TestSets {
      val func_1 = (x: Int) => x % 2 == 0
      val func_2 = (x: Int) => x < 0
      val temp_s = union(s1, s2)
      val s = union(temp_s, s3)
      assert(exists(s, func_1), "there exists a integer could be dived by 2")
      assert(!exists(s, func_2), "there is no integer below 0")
    }
  }

  test("map return a transformed set") {
    new TestSets {
      val temp_s = union(s1, s2)
      val s = union(temp_s, s3)
      val func = (x: Int) => x + 1
      assert(contains(map(s, func), 4), "4 is in set (2, 3, 4)")
      assert(!contains(map(s, func), 1), "1 is not in set (2, 3, 4)")
    }
  }
}
