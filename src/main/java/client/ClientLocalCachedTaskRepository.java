package client;

import common.Task;

import java.util.List;

public class ClientLocalCachedTaskRepository {
    private static List<Task> localCachedTaskList;

    public static List<Task> getLocalCachedTaskList() {
        return localCachedTaskList;
    }

    public static void setLocalCachedTaskList(List<Task> localCachedTaskList) {
        ClientLocalCachedTaskRepository.localCachedTaskList = localCachedTaskList;
    }
}
