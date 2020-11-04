package server;

import common.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ServerTaskRepository {
    private static List<Task> taskList = new LinkedList<>();

    public static Task addTaskToRepository(Task task) {
        taskList.add(task);
        Server.notifyAllClients(taskList);

        return task;
    }

    public static Task patchTaskInRepository(Task task) {
        Optional<Task> taskFromRep = taskList.stream()
                .filter(t -> t.getId().equals(t.getId()))
                .findFirst();

        if (taskFromRep.isPresent()) {
            taskList.remove(taskFromRep);
        }

        addTaskToRepository(task);
        return task;
    }

    public static Task deleteTaskFromRepository(String taskId) {
        taskList.stream()
                .filter(taskFromRep -> taskFromRep.getId().equals(taskId))
                .findFirst()
                .map(taskFromRep -> {
                    taskList.remove(taskFromRep);
                    Server.notifyAllClients(taskList);
                    return taskFromRep;
                });
        return null;
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
