import {Component, OnInit} from '@angular/core';
import {DashboardService} from './dashboard.service';
import {LancamentoFinapp} from '../entities/lancamento/lancamento-finapp.model';
import {JhiAlertService} from 'ng-jhipster';
import {ResponseWrapper} from '../shared';
import {Observable} from 'rxjs';
import {DecimalPipe} from '@angular/common';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styles: []
})
export class DashboardComponent implements OnInit {

    lancamentosDespesas: LancamentoFinapp[];
    lancamentosReceitas: LancamentoFinapp[];
    datasExibirList: any[] = [];
    dataSelecionada: any;
    listaDespesasAgrupadaCategoria: any[];
    listaSomaResultadoMesAgrupaTipo: any[];
    view: any[] = [700, 300];
    color: any = 'picnic';
    legendTitle: any = 'Legenda';
    listaContas: any[];
    colorBackground: any = '#232838';

    constructor(private dashboardService: DashboardService,
                private alertService: JhiAlertService) {
    }

    ngOnInit() {
        this.dataSelecionada = new Date();
        this.loadDatas();
        this.loadAll();
    }

    loadAll() {
        this.loadAllDespesas();
        this.loadAllReceitas();
        this.loadGraficos();
        this.getALlContas();
    }

    loadGraficos() {
        this.getDespesasCategoriaAgrupadoMes();
        this.getAllByDateGroupByTipo();
    }

    loadAllDespesas() {
        this.dashboardService.getDespesas(this.dataSelecionada).subscribe(
            (res: ResponseWrapper) => this.lancamentosDespesas = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadAllReceitas() {
        this.dashboardService.getReceitas(this.dataSelecionada).subscribe(
            (res: ResponseWrapper) => this.lancamentosReceitas = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadDatas() {
        this.datasExibirList = [];
        const dataAtual: Date = new Date(this.dataSelecionada.setDate(1));
        const data: any = new Object();
        data.data = dataAtual;
        data.checked = true;
        this.addDatasAnteriores(dataAtual);
        this.datasExibirList.push(data);
        this.addDatasPosteriores(dataAtual);
    }

    private addDatasAnteriores( dataAtual: Date ) {
        for ( let i = 3 ; i > 0; i-- ) {
            const data: any = new Object();
            data.data = new Date(dataAtual);
            data.data.setMonth(data.data.getMonth() - i);
            data.checked = false;
            this.datasExibirList.push(data);
        }
    }

    private addDatasPosteriores( dataAtual: Date ) {
        for ( let i = 1 ; i <= 3; i++ ) {
            const data: any = new Object();
            data.data = new Date(dataAtual);
            data.data.setMonth(data.data.getMonth() + i);
            data.checked = false;
            this.datasExibirList.push(data);
        }
        console.log(this.datasExibirList);
    }

    getDespesasCategoriaAgrupadoMes() {
        this.dashboardService.getListaCategoriaAgrupado(this.dataSelecionada, 'DESPESA').subscribe(
            (res: ResponseWrapper) => this.listaDespesasAgrupadaCategoria = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    getAllByDateGroupByTipo() {
        this.dashboardService.getAllByDateGroupByTipo(this.dataSelecionada).subscribe(
            (res: ResponseWrapper) => this.listaSomaResultadoMesAgrupaTipo = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    changeDate(selected: any) {
        this.dataSelecionada = new Date(selected.data);
        this.loadAllDespesas();
        this.loadAllReceitas();
        this.loadDatas();
        this.loadGraficos();
    }

    marcaPagamento( event: any, lancamento: LancamentoFinapp) {
        lancamento.pagoRecebido = event;
        this.dashboardService.update(lancamento).subscribe(() => {
            this.getALlContas();
        }, (response) => this.onSaveError());
    }

    getALlContas() {
        this.dashboardService.getAllContas().subscribe(
            (res: ResponseWrapper) => this.listaContas = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onSaveError() {
        this.alertService.error('Ocorreu um erro ao alterar o status de pagamento.');
    }

    formataTooltip(a: any) {
        return 'R$' + new DecimalPipe('pt-br').transform(a.value, '1.2-2');
    }

    labelFormatting(a: any) {
        return 'Saldo Atual da Conta ' + a.label;
    }
}
