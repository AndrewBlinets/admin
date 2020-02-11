package by.ipps.admin.exception;

public class InvalidJwtAuthenticationException extends Throwable {
  public InvalidJwtAuthenticationException(String expired_or_invalid_jwt_token) {}
}
