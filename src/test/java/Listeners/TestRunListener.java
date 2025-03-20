package Listeners;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.Plugin;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import stepdefinitions.ApplicationHooks;

public class TestRunListener implements Plugin, ConcurrentEventListener {

    public TestRunListener() {
        // Empty constructor
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, event -> {
            System.out.println("Test Run Started");
        });

        publisher.registerHandlerFor(TestRunFinished.class, event -> {
            System.out.println("Test Run Finished");
            ApplicationHooks.closeBrowser();
        });
    }
}