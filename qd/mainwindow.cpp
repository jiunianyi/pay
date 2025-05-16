#include "mainwindow.hxx"
#include "httpmgr.hxx"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    log = new longin(this);
    //mon = new money(this);
    reg = new reggister();
    this->hide();
    this->close();
    log->show();
    connect(httpmgr::getinstance().get(),&httpmgr::denglu,this,&MainWindow::long_in);
    connect(log,&longin::zhuce,this,&MainWindow::zhu_ce);
    connect(reg,&reggister::returnlog,this,&MainWindow::acceptret);
    connect(reg,&reggister::noreg,this,&MainWindow::acceptnoreg);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::long_in()
{
    log->hide();
    this->hide();
    //setCentralWidget(mon);
    money::getInstance().show();
}

void MainWindow::zhu_ce()
{
    this->hide();
    log->hide();
    reg->show();
}

void MainWindow::acceptret()
{
    reg->hide();
     log->show();
}

void MainWindow::acceptnoreg()
{
    reg->hide();
    log->show();
}
