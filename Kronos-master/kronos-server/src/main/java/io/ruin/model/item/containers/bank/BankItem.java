package io.ruin.model.item.containers.bank;

import com.google.gson.annotations.Expose;
import io.ruin.model.item.Item;

import java.util.Map;

public class BankItem extends Item {

    @Expose protected int tab;

    protected int sortSlot = -1;

    public BankItem(int id, int amount, int tab, Map<String, String> attibutes) {
        super(id, amount, attibutes);
        this.tab = tab;
    }

    @Override
    public BankItem copy() {
        return new BankItem(getId(), getAmount(), tab, copyOfAttributes());
    }

    public void toBlank() {
        setId(Bank.BLANK_ID);
        setAmount(0);
    }

}
