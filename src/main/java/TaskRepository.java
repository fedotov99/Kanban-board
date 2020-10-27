import java.util.LinkedList;
import java.util.List;

public class TaskRepository {
    private static List<Task> taskList = new LinkedList<>();

    public static Task addTaskToRepository(Task task) {
        taskList.add(task);
        Server.notifyAllClients(taskList);

        return task;
    }

    static {
        Task task1 = new Task();
        task1.setPriority(1);
        task1.setDescription("1");
        Task task2 = new Task();
        task2.setPriority(2);
        task2.setDescription("2");
        taskList.add(task1);
        taskList.add(task2);
    }

    public static List<Task> getTaskList() {
        return taskList;
    }
}
