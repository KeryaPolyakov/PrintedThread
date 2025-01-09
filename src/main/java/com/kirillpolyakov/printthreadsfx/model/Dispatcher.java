package com.kirillpolyakov.printthreadsfx.model;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Диспетчер помещает в очередь печати неограниченное количество документов.
 * При этом каждый документ может быть обработан, только если в это же время
 * не обрабатывается другой документ, время обработки каждого документа равно
 * продолжительности печати данного документа.
 */
@Data
public class Dispatcher {

    private boolean isWork = true;

    private Document currentPrintingDocument;

    private BlockingQueue<Document> documentsToPrint = new LinkedBlockingQueue<>();

    private List<Document> printedDocuments = new ArrayList<>();


    private Thread printThread;


    private List<Document> notPrintedDocuments = new ArrayList<>();

    private ListView<Document> listViewToPrint;

    public Dispatcher(ListView<Document> listView) {
        listViewToPrint = listView;
    }

    public void createThread() {
        printThread = new Thread(() -> {
            try {
                while (true) {
                    currentPrintingDocument = documentsToPrint.take();
                    Thread.sleep(currentPrintingDocument.getPrintDuration() * 1000);
                    print(currentPrintingDocument);
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                listViewToPrint.getItems().remove(0);
                            }
                        });
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            } catch (InterruptedException e) {
            }
        });
        printThread.start();
    }

    /**
     * Остановка диспетчера. Печать документов в очереди отменяется.
     * На выходе должен быть список ненапечатанных документов.
     */
    public void stopPrint() {
        printThread.interrupt();
        getNotPrinted();
        isWork = false;
    }

    public void getNotPrinted() {
        notPrintedDocuments.addAll(documentsToPrint);
        if (!listViewToPrint.getItems().isEmpty()) {
            notPrintedDocuments.add(currentPrintingDocument);
        }
    }

    /**
     * Принять документ на печать. Метод не должен блокировать выполнение программы.
     */
    public void addDocumentToPrint(Document document) {
        documentsToPrint.add(document);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                listViewToPrint.getItems().add(document);
            }
        });
    }

    /**
     * Отменить печать принятого документа, если он еще не был напечатан.
     */
    public void cancelPrintDocument(Document document) {
        if (isWork) {
            if (!documentsToPrint.remove(document)) {
                printThread.interrupt();
                createThread();
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    listViewToPrint.getItems().remove(document);
                }
            });
        }
    }

    /**
     * Получить отсортированный список напечатанных документов. Список
     * может быть отсортирован на выбор: по порядку печати,
     * по типу документов, по продолжительности печати, по размеру бумаги.
     */
    public List<Document> getSortedListOfPrintedDocuments(Comparator<Document> comparator) {
        List<Document> res = new ArrayList<>(printedDocuments);
        res.sort(comparator);
        return res;
    }

    /**
     * Рассчитать среднюю продолжительность печати напечатанных документов
     */
    public double getAvrTimePrintedDocuments() {
        double res = 0;
        for (Document document : printedDocuments) {
            res += document.getPrintDuration();
        }
        res /= printedDocuments.size();
        return res;
    }

    /**
     * Печать документа из очереди
     */
    public void print(Document document) {
        printedDocuments.add(document);
    }



}
