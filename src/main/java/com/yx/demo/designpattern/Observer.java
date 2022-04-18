package com.yx.demo.designpattern;

import java.util.ArrayList;
import java.util.List;

// 抽象观察者

interface Observer {
    void response(Long taskId); // 反应
}
// 抽象目标

abstract class Subject {
    protected List<Observer> observers = new ArrayList<Observer>();
    // 增加观察者方法

    public void add(Observer observer) {
        observers.add(observer);
    }
    // 删除观察者方法

    public void remove(Observer observer) {
        observers.remove(observer);
    }
    // 通知观察者方法

    public void notifyObserver(Long taskId) {
        for (Observer observer : observers) {
            observer.response(taskId);
        }
    }
}
// 活动观察者

class ActivityObserver implements Observer {
    private ActivityService activityService;
    @Override
    public void response(Long taskId) {
        activityService.notifyFinished(taskId);
    }
}
// 任务管理观察者

class TaskManageObserver implements Observer {
    private TaskManager taskManager;
    @Override
    public void response(Long taskId) {
        taskManager.release(taskId);
    }
}