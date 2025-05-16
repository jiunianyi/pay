#include "payjiem.hxx"
#include "ui_payjiem.h"
#include"httpmgr.hxx"
#include<QMessageBox>
#include<QPainter>
#include"qrcodegen.hpp"

using qrcodegen::QrCode;
using qrcodegen::QrSegment;
payjiem::payjiem(QWidget *parent)
    : QDialog(parent)
    , ui(new Ui::payjiem)
{
    ui->setupUi(this);

    //connect(&money::getInstance(),&money::selectpay,this,&payjiem::paybtn);
   // connect(mon,&money::selectpay,this,&payjiem::paybtn);
    connect(httpmgr::getinstance().get(),&httpmgr::createrma,this,&payjiem::creatma);
}

payjiem::~payjiem()
{
    delete ui;
}


void payjiem::on_pushButton_2_clicked()
{
    QJsonObject obj;
    obj["pay"]="5";
    QString gate_url_prefix = "http://localhost:8081";
    httpmgr::getinstance()->httppost(QUrl(gate_url_prefix+"/acc/pay"),obj);
}

void payjiem::creatma(QString &text)
{
    int pixelPerModule = 25;
    int border =5;
    try{
        qrcodegen::QrCode qr = qrcodegen::QrCode::encodeText(text.toUtf8(),QrCode::Ecc::MEDIUM);

        int qrSize = qr.getSize();
        int imageSize = (qrSize + 2 * border) * pixelPerModule;

        QImage image(imageSize, imageSize, QImage::Format_RGB32);
        image.fill(Qt::white);

        QPainter painter(&image);
        painter.setPen(Qt::NoPen);
        painter.setBrush(Qt::black);

        for (int y = 0; y < qrSize; y++) {
            for (int x = 0; x < qrSize; x++) {
                if (qr.getModule(x, y)) {
                    QRectF rect((x + border) * pixelPerModule,
                                (y + border) * pixelPerModule,
                                pixelPerModule,
                                pixelPerModule);
                    painter.drawRect(rect);
                }
            }
        }
        painter.end();
        // 将 QImage 转换为 QPixmap
        QPixmap pixmap = QPixmap::fromImage(image);

        // 获取 QLabel 的大小
        QSize labelSize = ui->label->size();

        // 将 QPixmap 缩放到 QLabel 的大小
        QPixmap scaledPixmap = pixmap.scaled(labelSize, Qt::KeepAspectRatio, Qt::SmoothTransformation);

        // 设置缩放后的 QPixmap 到 QLabel
        ui->label->setPixmap(scaledPixmap);

    }catch(std::exception &e){
        QMessageBox::critical(this, "错误", QString("生成二维码时发生错误: %1").arg(e.what()));

    }
}

