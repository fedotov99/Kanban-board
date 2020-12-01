package server;

import common.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ServerTaskRepository {
    private static List<Task> taskList = new LinkedList<>();

    public static Task addTaskToRepository(Task task, Integer indexOfExistingTask) {
        if (indexOfExistingTask != null) {
            taskList.add(indexOfExistingTask, task);
        } else {
            taskList.add(task);
        }
        Server.notifyAllClients(taskList);

        return task;
    }

    public static Task patchTaskInRepository(Task task) {
        Optional<Task> taskFromRep = taskList.stream()
                .filter(t -> t.getId().compareTo(task.getId()) == 0)
                .findFirst();
        Integer indexOfExistingTask = taskList.indexOf(taskFromRep.get());

        if (taskFromRep.isPresent()) {
            taskList.remove(taskFromRep.get());
        }

        addTaskToRepository(task, indexOfExistingTask);
        return task;
    }

    public static Task deleteTaskFromRepository(UUID taskId) {
        taskList.stream()
                .filter(taskFromRep -> taskFromRep.getId().compareTo(taskId) == 0)
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
        task1.setPriority(10);
        task1.setDescription("I'm a first task");
        Task task2 = new Task();
        task2.setPriority(5);
        task2.setDescription("I'm a second task");
        Task task3 = new Task();
        task3.setPriority(5);
        task3.setDescription("I'm a third task");
        Task task4 = new Task();
        task4.setPriority(5);
        task4.setDescription("I'm a fourth task");
        Task task5 = new Task();
        task5.setPriority(5);
        task5.setDescription("I'm a fiveth task");
        Task task6 = new Task();
        task6.setPriority(5);
        task6.setDescription("I'm a sixth task");
        Task task7 = new Task();
        task7.setPriority(5);
        task7.setDescription("I'm a seventh task");
        Task task8 = new Task();
        task8.setPriority(5);
        task8.setDescription("I'm an eighth task");
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);
        taskList.add(task6);
        taskList.add(task7);
        taskList.add(task8);
    }

    public static List<Task> getTaskList() {
        return taskList;
    }
}
