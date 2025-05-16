#include "httpmgr.hxx"
#include <QNetworkReply>
int zm = 0;
void httpmgr::httppost(QUrl url, QJsonObject obj)
{

    QByteArray date = QJsonDocument(obj).toJson();
    QNetworkRequest request(url);
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setHeader(QNetworkRequest::ContentLengthHeader,QByteArray::number(date.length()));
    QNetworkReply *reply = manger.post(request, date);
    QObject::connect(reply,&QNetworkReply::finished,[reply, this](){
        if(reply->error()==QNetworkReply::NoError)
        {
            QByteArray responseData = reply->readAll();
            qDebug()<<responseData;
            // 解析 JSON 数据
            QJsonDocument jsonDoc = QJsonDocument::fromJson(responseData);
          
            if (jsonDoc.isNull()) {
                qDebug() << "Failed to create JSON doc.";
                return;
            }
            QJsonObject jsonObj = jsonDoc.object();
            qDebug()<<jsonObj["fh"];
            if(jsonObj["fh"].toInt()==1001)
            {

                emit denglu();
            }
            if(jsonObj["fh"].toInt()==1002)
            {

                //qDebug()<<jsonObj["yzm"].toInt();
                zm = jsonObj["yzm"].toInt();
            }
            if(jsonObj["fh"].toInt()==1003)
            {
                emit showti(1003);
            }
            if(jsonObj["fh"].toInt()==1004)
            {
                emit showti(1004);
            }
            if(jsonObj["fh"].toInt()==1005)
            {
                QString text = jsonObj["ma"].toString();
                emit createrma(text);
            }
        }
        else {
            qDebug() << "Error:" << reply->errorString();
        }
    });
}

httpmgr::httpmgr()
{

}
