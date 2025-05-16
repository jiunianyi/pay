#ifndef PAYJIEM_HXX
#define PAYJIEM_HXX

#include <QDialog>
#include"money.hxx";

namespace Ui {
class payjiem;
}

class payjiem : public QDialog
{
    Q_OBJECT

public:
    static payjiem& getInstance() {
        static payjiem instance; // 局部静态变量
        return instance;
    }
    explicit payjiem(QWidget *parent = nullptr);
    ~payjiem();

private:
    Ui::payjiem *ui;

private slots:
    void on_pushButton_2_clicked();
    void creatma(QString &text);
};

#endif // PAYJIEM_HXX
