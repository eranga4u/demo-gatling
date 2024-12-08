package com.eranga.reconcile;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public class ReconcileResult {

    private static ReconcileResult instance;

    private List<Record> kos = new ArrayList<>();
    private List<Record> oks = new ArrayList<>();
    private List<Record> totalRequests = new ArrayList<>();


    public synchronized static ReconcileResult newInstance() {
        if (instance == null) {
            instance = new ReconcileResult();
        }
        return instance;
    }

    public synchronized static ReconcileResult getInstance() {
        return instance;
    }

    public void setOk(String id, String statusCode) {
        this.oks.add(Record.builder().id(id).date(getDateAndTime()).statusCode(statusCode).build());
    }

    public void setKo(String id, String statusCode) {
        this.kos.add(Record.builder().id(id).date(getDateAndTime()).statusCode(statusCode).build());
    }

    public void addRequest(String id, String statusCode) {
        this.totalRequests.add(Record.builder().id(id).date(getDateAndTime()).statusCode(statusCode).build());
    }

    public ArrayList<Record> getOkDuplicate() {
        return getDuplicates(this.oks);
    }

    public ArrayList<Record> getKoDuplicate() {
        return getDuplicates(this.kos);
    }


    public ArrayList<Record> getDuplicates() {
        ArrayList<Record> merger = new ArrayList<>(this.getKoDuplicate());
        merger.addAll(this.getOkDuplicate());
        return merger;
    }

    private void saveOkRecords() {
        this.saveToJson(this.oks, "ok.json");
    }

    private void saveKoRecords() {
        this.saveToJson(this.kos, "ko.json");
    }

    private void saveDuplicates() {
        this.saveToJson(this.getDuplicates(), "duplicates.json");
    }

    private ArrayList<Record> getDuplicates(List<Record> records) {
        HashSet<String> uniqueElements = new HashSet<>();
        Set<String> uniqueIds = new HashSet<>();
        return records.stream().filter(item -> !uniqueElements.add(item.getId()))
            .filter(item -> uniqueIds.add(item.getId())).collect(
                Collectors.toCollection(ArrayList::new));
    }

    private void saveToJson(List<Record> records, String fileName) {
        // Write the records to a JSON file
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), records);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Records saved to ={}", fileName);
        System.out.println("Records saved to = " + fileName);
    }

    public void printSummaryAndSaveToJson() {
        System.out.println("**********************************");

        System.out.println("OK records - " + this.getOks().size());
        System.out.println("KO records - " + this.getKos().size());

        System.out.println("Duplicate Ok records - " + this.getOkDuplicate().size());
        System.out.println("Duplicate KO records - " + this.getKoDuplicate().size());

        System.out.println("Total Duplicate records - " + this.getDuplicates().size());

        saveKoRecords();
        saveOkRecords();
        saveDuplicates();

        System.out.println("**********************************");

    }

    private String getDateAndTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

}
