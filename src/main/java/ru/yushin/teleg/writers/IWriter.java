package ru.yushin.teleg.writers;


import ru.yushin.teleg.model.Message;

/**
 * Интерфейс для записи информации в файлы формата .txt .xlsx
 * При необходимости записи в какой либо другой файл, создать его класс в пакете writers и имплементировать этот интерфейс
 */
public interface IWriter {
    void write(Message message);
}
