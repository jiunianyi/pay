#include "reggister.hxx"
#include "ui_reggister.h"
#include<QJsonObject>
#include<QJsonDocument>
#include"httpmgr.hxx"
#include<QTimer>
#include<QMessageBox>
#include<const.h>
reggister::reggister(QWidget *parent)
    : QDialog(parent),timer(new QTimer(this))
    , ui(new Ui::reggister)
{
    ui->setupUi(this);
    ui->label_2->setText("确认密码");
    // 连接定时器的超时信号
    connect(timer, &QTimer::timeout, this, &reggister::onTimeout);
    connect(httpmgr::getinstance().get(),&httpmgr::showti,this,&reggister::showti);
}

reggister::~reggister()
{
    delete ui;
}

void reggister::on_pushButton_2_clicked()
{
    QString user = ui->user_ed->text();
    auto pwd = ui->pwd_ed->text();
    auto phone = ui->phon_ed->text();
    auto repwd = ui->repwd_ed->text();
    auto yzm = ui->zy_ed->text();
    if(pwd!=repwd)
    {
        ui->label_6->setText("请保持两次密码一致");
        return;
    }
    QJsonObject obj;
    obj["username"]=user;
    obj["phone"]=phone;
    obj["pwd"]=pwd;
    obj["repwd"]=repwd;
    QString gate_url_prefix = "http://localhost:8081";
    if(ui->zy_ed->text().toInt()==zm)
    {
        ui->label_6->setText("");
    httpmgr::getinstance()->httppost(QUrl(gate_url_prefix+"/acc/register"),obj);
    }
    else{
        ui->label_6->setText("验证码错误");
        return;
    }

}


void reggister::on_pushButton_3_clicked()
{
    this ->hide();
    emit noreg();
}




void reggister::onTimeout()
{
    remainingTime--;
    ui->get_but->setText(QString::number(remainingTime));
    if(remainingTime<=0)
    {
        timer->stop();
        ui->get_but->setEnabled(true);
        ui->get_but->setText("获取");
        remainingTime=60;
    }
}


void reggister::on_get_but_clicked()
{
    ui->get_but->setEnabled(false);
    timer->start(1000);
    QJsonObject obj;
    obj["phone"]=ui->phon_ed->text();
     httpmgr::getinstance()->httppost(QUrl("http://localhost:8081/acc/getyzm"),obj);
     QMessageBox::information(this, "提示", "验证码已发送到您的手机");
}

void reggister::showti(int i)
{
    if(i==1003)
    {
    qDebug()<<"注册成功";
    ui->label_6->setText("注册成功请返回登陆");
    }else if(i==1004)
    {
        ui->label_6->setText("账号已注册");
    }
}


void reggister::on_pushButton_clicked()
{
    emit returnlog();
}

