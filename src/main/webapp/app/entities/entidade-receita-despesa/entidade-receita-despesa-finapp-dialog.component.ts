import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntidadeReceitaDespesaFinapp } from './entidade-receita-despesa-finapp.model';
import { EntidadeReceitaDespesaFinappPopupService } from './entidade-receita-despesa-finapp-popup.service';
import { EntidadeReceitaDespesaFinappService } from './entidade-receita-despesa-finapp.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-entidade-receita-despesa-finapp-dialog',
    templateUrl: './entidade-receita-despesa-finapp-dialog.component.html'
})
export class EntidadeReceitaDespesaFinappDialogComponent implements OnInit {

    entidadeReceitaDespesa: EntidadeReceitaDespesaFinapp;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private entidadeReceitaDespesaService: EntidadeReceitaDespesaFinappService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entidadeReceitaDespesa.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entidadeReceitaDespesaService.update(this.entidadeReceitaDespesa));
        } else {
            this.subscribeToSaveResponse(
                this.entidadeReceitaDespesaService.create(this.entidadeReceitaDespesa));
        }
    }

    private subscribeToSaveResponse(result: Observable<EntidadeReceitaDespesaFinapp>) {
        result.subscribe((res: EntidadeReceitaDespesaFinapp) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EntidadeReceitaDespesaFinapp) {
        this.eventManager.broadcast({ name: 'entidadeReceitaDespesaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-entidade-receita-despesa-finapp-popup',
    template: ''
})
export class EntidadeReceitaDespesaFinappPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entidadeReceitaDespesaPopupService: EntidadeReceitaDespesaFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.entidadeReceitaDespesaPopupService
                    .open(EntidadeReceitaDespesaFinappDialogComponent as Component, params['id']);
            } else {
                this.entidadeReceitaDespesaPopupService
                    .open(EntidadeReceitaDespesaFinappDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
