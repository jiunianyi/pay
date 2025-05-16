#include "longin.hxx"
#include "ui_longin.h"
#include<QJsonObject>
#include<QJsonDocument>
#include"httpmgr.hxx"
#include<QUrl>
longin::longin(QWidget *parent)
    : QDialog(parent)
    , ui(new Ui::longin)
{
    ui->setupUi(this);

}

longin::~longin()
{
    delete ui;
}



void longin::on_pushButton_clicked()
{
    QString zh = ui->zh_edit->text();
    QString mm = ui->mm_edit->text();
    QJsonObject obj;
    obj["zh"]=zh;
    obj["mm"]=mm;
    QString gate_url_prefix = "http://localhost:8081";
    httpmgr::getinstance()->httppost(QUrl(gate_url_prefix+"/acc/doLogin"),obj);
}


void longin::on_pushButton_2_clicked()
{
    emit zhuce();
}



