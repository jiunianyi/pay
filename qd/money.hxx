#ifndef MONEY_HXX
#define MONEY_HXX

#include <QDialog>

namespace Ui {
class money;
}

class money : public QDialog
{
    Q_OBJECT

public:
    static money& getInstance() {
        static money instance; // 局部静态变量
        return instance;
    }
    explicit money(QWidget *parent = nullptr);
    ~money();

private:
    Ui::money *ui;
private slots:
    void on_pushButton_2_clicked();
signals:
    void selectpay();
};

#endif // MONEY_HXX
