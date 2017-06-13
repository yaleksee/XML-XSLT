package magnit.xml;

import magnit.pojo.Entry;
import magnit.Initial.Variables;
import magnit.dao.DAOManager;
import magnit.dao.DAOException;

import java.util.ArrayList;
import java.util.concurrent.*;

import static magnit.Initial.DataBaseVariables.getN;

public class DAOresImpl {
    private DAOManager daoManager;

    public DAOresImpl() {
        this.daoManager = new DAOManager();
    }

    /**
     * Получаем список всех вхождений в БД
     *
     * @return ArrayList<Entry>
     */
    public ArrayList<Entry> selectFieldsFromDatabase() {
        ArrayList<Entry> entries = null;
        try {
            entries = daoManager.selectFieldsFromDataBase();
        } catch (DAOException e) {
            System.err.println("не удалось получить значение из БД, " + e.getMessage());
            System.exit(0);
        }
        return entries;
    }

    /**
     * Удаление всех записей из БД и запись новых значений, используя многопоточность
     */
    public void updateFieldsInDatabase() {
        int fieldCount = getN();
        int batchCount = fieldCount / Variables.PACKAGE_SIZE;
        int rest = fieldCount % Variables.PACKAGE_SIZE;
        try {
            daoManager.deleteAllFieldsFromDatabase();
        } catch (DAOException e) {
            System.err.println("не удалось удалить значения из БД " + e.getMessage());
            System.exit(0);
        }
        Integer finishedTasks = 0;
        /*
        ExecutorService исполняет асинхронный код в одном или нескольких потоках.
        Создание инстанса ExecutorService'а делается либо вручную через конкретные имплементации
        (ScheduledThreadPoolExecutor или ThreadPoolExecutor), но проще будет использовать фабрики класса Executors.
        Например, если надо создать пул с 2мя потоками, то делается это так:
        ExecutorService service = Executors.newFixedThreadPool(2);
        Если требуется использовать кэширующий пул потоков, который создает потоки по мере необходимости,
        но переиспользует неактивные потоки (и подчищает потоки, которые были неактивные некоторое время),
        то это задается следующим образом:
        ExecutorService service = Executors.newCachedThreadPool();
         */
        ExecutorService executorService = Executors.newFixedThreadPool(Variables.POOL_SIZE);
        ArrayList<Future<Integer>> insertedBatch = new ArrayList<>();
        for (int batch = 0; batch < batchCount; batch++) {
            FieldsBox fieldsBox = new FieldsBox(batch * Variables.PACKAGE_SIZE, batch * Variables.PACKAGE_SIZE + Variables.PACKAGE_SIZE);
            Future<Integer> result = executorService.submit(fieldsBox);
            insertedBatch.add(result);
        }
        FieldsBox fieldsBox = new FieldsBox(batchCount * Variables.PACKAGE_SIZE, batchCount * Variables.PACKAGE_SIZE + rest);
        /*
        Future обеспечивает метод cancel() для отмены Callable таска.
        Future хранит результат асинхронного вычисления.
        Вы можете запустить вычисление, предоставив кому-либо объект Future, и забыть о нем.
        Владелец объекта Future может получить результат, когда он будет готов.
         */
        Future<Integer> result = executorService.submit(fieldsBox);
        insertedBatch.add(result);

        while (finishedTasks < batchCount + 1) {
            finishedTasks = 0;
            for (Future<Integer> future : insertedBatch) {
                try {
                    finishedTasks += future.get();
                } catch (InterruptedException | ExecutionException ex) {
                    System.err.println("Не удалсь добавить значение в БД, " + ex.getMessage());
                    executorService.shutdown();
                    System.exit(0);
                }
            }
        }
        executorService.shutdown();

    }

    /**
     * Подкласс для многопоточной обработки процесса записи данных в БД
     * может вернуть результат в виде объекта Object и способен бросать исключения.

     Интерфейс Callable использует дженерики для определения типа возвращаемого объекта.
     Класс Executors предоставляет полезные методы для выполнения Callable в пуле потоков.
     Callable таски (задачи) возвращают java.util.concurrent.Future объект.
     Используя Future мы можем узнать статус Callable таска и получить возвращенный объект.
     Это обеспечивает get() метод, который ждет завершение Callable, чтобы вернуть результат.
     */
    class FieldsBox implements Callable<Integer> {
        private Integer from;
        private Integer to;

        public FieldsBox(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public Integer call() throws Exception {
            try {
                daoManager.insertFieldsToDatabase(from, to);
            } catch (DAOException e) {
                throw new Exception("Ощибка работы потока");
            }
            return 1;
        }
    }
}
