#ifndef MAINWINDOW_HXX
#define MAINWINDOW_HXX

#include <QMainWindow>
#include"longin.hxx"
#include"money.hxx"
#include"reggister.hxx";
QT_BEGIN_NAMESPACE
namespace Ui {
class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void long_in();
    void zhu_ce();
    void acceptret();
    void acceptnoreg();
private:
    reggister * reg;
    longin * log;
    // money * mon;
    Ui::MainWindow *ui;
};
#endif // MAINWINDOW_HXX
