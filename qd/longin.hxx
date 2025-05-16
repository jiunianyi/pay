#ifndef LONGIN_HXX
#define LONGIN_HXX
#include <QDialog>
namespace Ui {
class longin;
}

class longin : public QDialog
{
    Q_OBJECT

public:
    explicit longin(QWidget *parent = nullptr);
    ~longin();

private slots:
    void on_pushButton_clicked();

    void on_pushButton_2_clicked();


private:

    Ui::longin *ui;
signals:
    void zhuce();
};

#endif // LONGIN_HXX
