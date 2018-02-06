import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LancamentoFinapp } from './lancamento-finapp.model';
import { LancamentoFinappPopupService } from './lancamento-finapp-popup.service';
import { LancamentoFinappService } from './lancamento-finapp.service';
import { ContaFinapp, ContaFinappService } from '../conta';
import { EntidadeReceitaDespesaFinapp, EntidadeReceitaDespesaFinappService } from '../entidade-receita-despesa';
import { CategoriaFinapp, CategoriaFinappService } from '../categoria';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lancamento-finapp-dialog',
    templateUrl: './lancamento-finapp-dialog.component.html'
})
export class LancamentoFinappDialogComponent implements OnInit {

    lancamento: LancamentoFinapp;
    isSaving: boolean;

    lancamentos: LancamentoFinapp[];

    contas: ContaFinapp[];

    entidadereceitadespesas: EntidadeReceitaDespesaFinapp[];

    categorias: CategoriaFinapp[];

    users: User[];
    dataDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private lancamentoService: LancamentoFinappService,
        private contaService: ContaFinappService,
        private entidadeReceitaDespesaService: EntidadeReceitaDespesaFinappService,
        private categoriaService: CategoriaFinappService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.lancamentoService.query()
            .subscribe((res: ResponseWrapper) => { this.lancamentos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.contaService.query()
            .subscribe((res: ResponseWrapper) => { this.contas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.entidadeReceitaDespesaService.query()
            .subscribe((res: ResponseWrapper) => { this.entidadereceitadespesas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.categoriaService.query()
            .subscribe((res: ResponseWrapper) => { this.categorias = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lancamento.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lancamentoService.update(this.lancamento));
        } else {
            this.subscribeToSaveResponse(
                this.lancamentoService.create(this.lancamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<LancamentoFinapp>) {
        result.subscribe((res: LancamentoFinapp) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LancamentoFinapp) {
        this.eventManager.broadcast({ name: 'lancamentoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackLancamentoById(index: number, item: LancamentoFinapp) {
        return item.id;
    }

    trackContaById(index: number, item: ContaFinapp) {
        return item.id;
    }

    trackEntidadeReceitaDespesaById(index: number, item: EntidadeReceitaDespesaFinapp) {
        return item.id;
    }

    trackCategoriaById(index: number, item: CategoriaFinapp) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-lancamento-finapp-popup',
    template: ''
})
export class LancamentoFinappPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lancamentoPopupService: LancamentoFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lancamentoPopupService
                    .open(LancamentoFinappDialogComponent as Component, params['id']);
            } else {
                this.lancamentoPopupService
                    .open(LancamentoFinappDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
