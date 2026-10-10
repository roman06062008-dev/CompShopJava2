package ru.compshop.demo;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.text.InputType;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(246,247,251), ink = Color.rgb(25,29,45),
            muted = Color.rgb(111,119,140), accent = Color.rgb(83,99,255),
            panel = Color.WHITE, green = Color.rgb(36,155,104);
    private LinearLayout root, body;
    private String screen = "home", category = "Игры", wishes = "";
    private int requestNo = 1042;
    private EditText wishInput;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(Color.WHITE);
        show("home");
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color); d.setCornerRadius(radius);
        return d;
    }
    private TextView text(String s, int size, int color, boolean bold) {
        TextView v = new TextView(this);
        v.setText(s); v.setTextSize(size); v.setTextColor(color);
        if (bold) v.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        v.setGravity(Gravity.CENTER_VERTICAL);
        return v;
    }
    private LinearLayout column() {
        LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); return l;
    }
    private LinearLayout row() {
        LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.HORIZONTAL);
        l.setGravity(Gravity.CENTER_VERTICAL); return l;
    }
    private void pad(View v, int left, int top, int right, int bottom) {
        v.setPadding(left, top, right, bottom);
    }
    private void space(LinearLayout into, int height) {
        View v = new View(this); into.addView(v, new LinearLayout.LayoutParams(1, height));
    }
    private TextView button(String label, boolean primary, Runnable action) {
        TextView b = text(label, 15, primary ? Color.WHITE : ink, true);
        b.setGravity(Gravity.CENTER); b.setPadding(18, 14, 18, 14);
        b.setBackground(shape(primary ? accent : Color.rgb(237,239,247), 18));
        b.setOnClickListener(v -> action.run());
        return b;
    }
    private LinearLayout card() {
        LinearLayout c = column(); c.setPadding(18,18,18,18); c.setBackground(shape(panel, 22));
        return c;
    }
    private void show(String which) {
        screen = which;
        root = column(); root.setBackgroundColor(bg);
        setContentView(root);
        LinearLayout header = row(); header.setPadding(22,16,22,12);
        TextView logo = text("◈  CompShop", 20, ink, true);
        header.addView(logo, new LinearLayout.LayoutParams(0, -2, 1));
        TextView role = text("КЛИЕНТ", 11, accent, true);
        header.addView(role);
        root.addView(header);
        ScrollView scroll = new ScrollView(this);
        body = column(); body.setPadding(20, 12, 20, 28);
        scroll.addView(body); root.addView(scroll, new LinearLayout.LayoutParams(-1,0,1));
        switch (which) {
            case "home": home(); break;
            case "catalog": catalog(); break;
            case "product": product(); break;
            case "request": request(); break;
            case "status": status(); break;
        }
        LinearLayout nav = row(); nav.setPadding(14,10,14,10); nav.setBackgroundColor(Color.WHITE);
        String[][] tabs = {{"⌂","Главная","home"},{"▦","Каталог","catalog"},{"◎","Заявка","status"}};
        for (String[] t : tabs) {
            LinearLayout item = column(); item.setGravity(Gravity.CENTER);
            item.addView(text(t[0],18, screen.equals(t[2]) ? accent : muted,true));
            item.addView(text(t[1],11, screen.equals(t[2]) ? accent : muted,false));
            item.setOnClickListener(v -> show(t[2]));
            nav.addView(item, new LinearLayout.LayoutParams(0,-2,1));
        }
        root.addView(nav);
    }
    private void heading(String eyebrow, String title, String desc) {
        TextView e = text(eyebrow.toUpperCase(),11,accent,true); body.addView(e);
        space(body,8);
        TextView h = text(title,29,ink,true); h.setLineSpacing(1.5f,1f); body.addView(h);
        space(body,7);
        TextView p = text(desc,15,muted,false); p.setLineSpacing(3,1f); body.addView(p);
        space(body,22);
    }
    private void home() {
        heading("ПК ПОД ТВОИ ЗАДАЧИ", "Твой следующий\nуровень.", "Для игр, работы и больших идей. Подберём конфигурацию под твой бюджет.");
        LinearLayout hero = card(); hero.setBackground(shape(Color.rgb(27,32,54),24));
        TextView chip = text("COMPSHOP • READY BUILD",11,Color.rgb(164,174,255),true); hero.addView(chip);
        space(hero,14);
        TextView pc = text("▰",70,Color.WHITE,true); pc.setGravity(Gravity.CENTER); hero.addView(pc);
        TextView title = text("Мощность внутри.",21,Color.WHITE,true); hero.addView(title);
        TextView desc = text("Сбалансированные комплектующие и тестирование перед выдачей.",13,Color.rgb(206,211,229),false); hero.addView(desc);
        space(hero,18);
        hero.addView(button("Выбрать компьютер  →",true,()->show("catalog")));
        body.addView(hero);
        space(body,24);
        body.addView(text("С чего начнём?",21,ink,true)); space(body,12);
        LinearLayout feature = card();
        feature.addView(text("✦  Игры и творчество",17,ink,true));
        space(feature,5); feature.addView(text("Готовые конфигурации для разных задач.",14,muted,false));
        space(feature,14); feature.addView(button("Смотреть сборки",false,()->show("catalog")));
        body.addView(feature);
    }
    private void catalog() {
        heading("ГОТОВЫЕ КОНФИГУРАЦИИ","Выбери свою мощность","Сборки для разных задач и бюджета.");
        LinearLayout filters = row();
        for (String cat : new String[]{"Все","Игры","Работа"}) {
            TextView b = text(cat,13, category.equals(cat)?Color.WHITE:ink,true);
            b.setGravity(Gravity.CENTER); b.setPadding(16,10,16,10);
            b.setBackground(shape(category.equals(cat)?accent:Color.WHITE,18));
            b.setOnClickListener(v->{ category=cat; show("catalog"); });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2,-2); lp.setMargins(0,0,8,0); filters.addView(b,lp);
        }
        body.addView(filters); space(body,16);
        LinearLayout c = card();
        TextView badge = text(category.equals("Работа")?"ДЛЯ РАБОТЫ":"ДЛЯ ИГР • FULL HD",11,accent,true); c.addView(badge);
        space(c,12);
        TextView art = text("▰",68,accent,true); art.setGravity(Gravity.CENTER); c.addView(art);
        space(c,8);
        c.addView(text(category.equals("Работа")?"COMPSHOP WORK":"COMPSHOP STANDART",20,ink,true));
        space(c,6); c.addView(text("Core i5  ·  RTX 4060\n32 ГБ DDR5  ·  SSD 1 ТБ",14,muted,false));
        space(c,16); c.addView(text("89 900 ₽",24,ink,true)); space(c,12);
        c.addView(button("Подробнее о сборке  →",true,()->show("product")));
        body.addView(c); space(body,14);
        TextView note = text("Итоговую конфигурацию и сроки согласует менеджер.",13,muted,false);
        body.addView(note);
    }
    private void product() {
        TextView back = button("←  Назад в каталог",false,()->show("catalog"));
        body.addView(back); space(body,18);
        heading("ГОТОВАЯ СБОРКА",category.equals("Работа")?"COMPSHOP WORK":"COMPSHOP STANDART","Надёжная конфигурация для ежедневных задач.");
        LinearLayout c = card();
        TextView art = text("▰",72,accent,true); art.setGravity(Gravity.CENTER); c.addView(art);
        space(c,12);
        c.addView(text("Характеристики",18,ink,true)); space(c,12);
        spec(c,"Процессор","Intel Core i5");
        spec(c,"Видеокарта","GeForce RTX 4060");
        spec(c,"Оперативная память","32 ГБ DDR5");
        spec(c,"Накопитель","SSD 1 ТБ");
        body.addView(c); space(body,18);
        TextView price = text("89 900 ₽",27,ink,true); body.addView(price);
        space(body,5); body.addView(text("Ориентировочная стоимость",13,muted,false));
        space(body,16);
        body.addView(button("Оформить заявку  →",true,()->show("request")));
    }
    private void spec(LinearLayout parent, String name, String value) {
        LinearLayout r = row(); LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,-2); lp.setMargins(0,0,0,10);
        r.addView(text(name,13,muted,false),new LinearLayout.LayoutParams(0,-2,1));
        r.addView(text(value,14,ink,true)); parent.addView(r,lp);
    }
    private void request() {
        heading("ЗАЯВКА НА СБОРКУ","Расскажите о пожеланиях","Мы передадим информацию менеджеру для согласования деталей.");
        LinearLayout c = card();
        c.addView(text("COMPSHOP STANDART",17,ink,true)); space(c,5);
        c.addView(text("Core i5 · RTX 4060 · 32 ГБ · SSD 1 ТБ",13,muted,false));
        space(c,12); c.addView(text("Ориентировочная цена: 89 900 ₽",14,accent,true));
        body.addView(c); space(body,18);
        body.addView(text("Пожелания к сборке (необязательно)",14,ink,true)); space(body,8);
        wishInput = new EditText(this); wishInput.setHint("Например: тихое охлаждение, белый корпус");
        wishInput.setText(wishes); wishInput.setTextSize(14); wishInput.setGravity(Gravity.TOP);
        wishInput.setMinLines(4); wishInput.setPadding(14,12,14,12); wishInput.setBackground(shape(Color.WHITE,16));
        body.addView(wishInput,new LinearLayout.LayoutParams(-1,-2)); space(body,16);
        body.addView(button("Отправить заявку",true,()->{
            wishes = wishInput.getText().toString().trim();
            requestNo++;
            show("status");
        }));
        space(body,8); body.addView(button("Отмена",false,()->show("product")));
        body.addView(text("Демонстрационный режим: данные не отправляются на сервер.",12,muted,false));
    }
    private void status() {
        heading("ЛИЧНЫЙ КАБИНЕТ","Заявка получена!","Спасибо за доверие. Менеджер уточнит детали и согласует сборку.");
        LinearLayout c = card();
        LinearLayout r = row();
        TextView check = text("✓",25,green,true); check.setGravity(Gravity.CENTER);
        check.setBackground(shape(Color.rgb(224,247,237),48));
        r.addView(check,new LinearLayout.LayoutParams(48,48));
        LinearLayout info = column(); info.setPadding(12,0,0,0);
        info.addView(text("Заявка № CS-"+requestNo,17,ink,true));
        info.addView(text("COMPSHOP STANDART",12,muted,false)); r.addView(info);
        c.addView(r); space(c,18);
        TextView badge = text("●  ОЖИДАЕТ ОБРАБОТКИ",11,green,true);
        c.addView(badge); body.addView(c); space(body,22);
        body.addView(text("Этапы заказа",20,ink,true)); space(body,15);
        timeline("✓","Заявка получена","Требования переданы менеджеру",true);
        timeline("2","Согласование конфигурации","Комплектующие, стоимость и срок",false);
        timeline("3","Сборка компьютера","Исполнитель приступает к работе",false);
        timeline("4","Тестирование","Проверка стабильности и температуры",false);
        timeline("5","Готов к выдаче","Менеджер согласует получение",false);
        space(body,16);
        body.addView(button("Вернуться в каталог",true,()->show("catalog")));
        body.addView(text("Статус показан для демонстрации интерфейса.",12,muted,false));
    }
    private void timeline(String num, String title, String desc, boolean done) {
        LinearLayout r = row(); r.setGravity(Gravity.TOP);
        TextView dot = text(num,13,done?Color.WHITE:muted,true); dot.setGravity(Gravity.CENTER);
        dot.setBackground(shape(done?green:Color.rgb(230,233,242),40));
        r.addView(dot,new LinearLayout.LayoutParams(32,32));
        LinearLayout words = column(); words.setPadding(12,0,0,12);
        words.addView(text(title,15,done?ink:muted,true));
        words.addView(text(desc,12,muted,false)); r.addView(words);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,-2); lp.setMargins(0,0,0,12);
        body.addView(r,lp);
    }
}
