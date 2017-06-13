package magnit.dao;

/**
 * Конструкторы:
 * DAOException по умолчанию;
 * DAOException с переданным сообщением;
 * DAOException с переданными параметрами;
 * DAOException с параметром throwable
 */

public class DAOException extends Exception {

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String p_arg0, Throwable p_arg1) {
        super(p_arg0, p_arg1);
    }

    public DAOException(Throwable p_arg0) {
        super(p_arg0);
    }

}