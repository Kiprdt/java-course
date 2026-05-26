package ru.vsu.atm;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AtmRunner implements CommandLineRunner {

    private final AtmConsole atmConsole;

    public AtmRunner(AtmConsole atmConsole) {
        this.atmConsole = atmConsole;
    }

    @Override
    public void run(String... args) {
        atmConsole.start();
    }
}