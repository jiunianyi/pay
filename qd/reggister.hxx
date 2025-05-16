#ifndef REGGISTER_HXX
#define REGGISTER_HXX

#include <QDialog>

namespace Ui {
class reggister;
}

class reggister : public QDialog
{
    Q_OBJECT

public:
    explicit reggister(QWidget *parent = nullptr);
    ~reggister();

private slots:
    void on_pushButton_2_clicked();

    void on_pushButton_3_clicked();

    void onTimeout();

    void on_get_but_clicked();

    void showti(int i);

    void on_pushButton_clicked();
signals:
    void returnlog();
    void noreg();
private:
    int remainingTime = 60;
    QTimer *timer;
    Ui::reggister *ui;
};

#endif // REGGISTER_HXX
