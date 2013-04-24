/**
* NoSolutionException represents an exception generated when
* the algorithm isn 't able to find a set of values that
* matches the variables , such that no restriction is violated
*/
public class NoSolutionException extends Exception
{
/**
* Creates a NoSolutionException with a 'message ' providing
* additional details .
* @param message String containing the message of the exception .
*/
public NoSolutionException ( String message ) {
super ( message );
}
/**
* Constructs a new NoSolutionException with the specified
* detail message and cause .
* @param message String containing the message of the exception
* @param cause Throwable cause of the exception .
*/
public NoSolutionException ( String message , Throwable cause ) {
super ( message , cause );
}
}