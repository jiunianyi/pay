#include "money.hxx"
#include "ui_money.h"
#include<QImage>
#include"payjiem.hxx"


money::money(QWidget *parent)
    : QDialog(parent)
    , ui(new Ui::money)
{
    ui->setupUi(this);
}

money::~money()
{
    delete ui;
}



void money::on_pushButton_2_clicked()
{
    this->hide();
    payjiem::getInstance().show();
}

