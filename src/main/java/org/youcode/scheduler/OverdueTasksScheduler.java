package org.youcode.scheduler;

import org.youcode.model.Task;
import org.youcode.model.enums.TaskStatus;
import org.youcode.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OverdueTasksScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final TaskService taskService = new TaskService();

    public void startOverdueTasksScheduler() {
        scheduler.scheduleAtFixedRate(this::resetTokens, 0, 1, TimeUnit.DAYS);
    }

    private void resetTokens() {
        // mark overdue tasks as overdue
        List<Task> tasks = taskService.getAllTasks();
        tasks.forEach(
                task -> {
                    if (task.getStatus().equals(TaskStatus.pending) && task.getDeadline().isBefore(LocalDate.now())) {
                        task.setStatus(TaskStatus.overdue);
                        taskService.updateTask(task);
                    }
                }
        );
    }

    public void stopOverdueTasksScheduler() {
        scheduler.shutdown();
    }
}
