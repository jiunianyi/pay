#ifndef HTTPMGR_HXX
#define HTTPMGR_HXX
#include "singleton.hxx"
#include <QNetworkAccessManager>
#include<QJsonObject>
#include<QJsonDocument>
#include<QObject>
#include<QUrl>
#include"const.h"
class httpmgr:public QObject,public singleton<httpmgr>
{
    Q_OBJECT

public:
    QNetworkAccessManager manger;
   void httppost(QUrl url,QJsonObject obj);
    httpmgr();
signals:
    void denglu();
    void showti(int i);
    void createrma(QString &text);

};

#endif // HTTPMGR_HXX
