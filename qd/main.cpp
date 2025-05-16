#include "mainwindow.hxx"

#include <QApplication>
#include<QFile>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
     QFile qss(":/style/style.qss");
    if(qss.open(QFile::ReadOnly))
    {
        qDebug("open success");
        QString style = QLatin1String(qss.readAll());
        a.setStyleSheet(style);
        qss.close();
    }else
    {
        qDebug("open flase");
    }
    MainWindow w;
  //  w.show();
    return a.exec();
}
