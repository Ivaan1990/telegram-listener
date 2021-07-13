package ru.yushin.teleg.transfer;

import ru.yushin.teleg.db.DataBaseService;
import ru.yushin.teleg.db.PostgresSql;
import ru.yushin.teleg.transfer.model.Message;

public class TransferDb implements ITransfer{

    DataBaseService dataBaseService;
    PostgresSql postgresSql;

    @Override
    public void transfer(Message message) {
        //dataBaseService.updateSqlScript("UPDATE DATABASE");
    }
}
