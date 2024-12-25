package io.ruin.api.database;

public class DummyDatabase extends Database {

    public DummyDatabase() {
        super(null, null, null, null);
    }

    @Override
    public void execute(DatabaseStatement statement) {

    }

    @Override
    public void executeAwait(DatabaseStatement statement) {

    }
}
