import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContaFinapp } from './conta-finapp.model';
import { ContaFinappPopupService } from './conta-finapp-popup.service';
import { ContaFinappService } from './conta-finapp.service';
import { TipoContaFinapp, TipoContaFinappService } from '../tipo-conta';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-conta-finapp-dialog',
    templateUrl: './conta-finapp-dialog.component.html'
})
export class ContaFinappDialogComponent implements OnInit {

    conta: ContaFinapp;
    isSaving: boolean;

    tipocontas: TipoContaFinapp[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contaService: ContaFinappService,
        private tipoContaService: TipoContaFinappService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tipoContaService
            .query({filter: 'conta-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.conta.tipoContaId) {
                    this.tipocontas = res.json;
                } else {
                    this.tipoContaService
                        .find(this.conta.tipoContaId)
                        .subscribe((subRes: TipoContaFinapp) => {
                            this.tipocontas = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.conta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contaService.update(this.conta));
        } else {
            this.subscribeToSaveResponse(
                this.contaService.create(this.conta));
        }
    }

    private subscribeToSaveResponse(result: Observable<ContaFinapp>) {
        result.subscribe((res: ContaFinapp) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ContaFinapp) {
        this.eventManager.broadcast({ name: 'contaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackTipoContaById(index: number, item: TipoContaFinapp) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-conta-finapp-popup',
    template: ''
})
export class ContaFinappPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contaPopupService: ContaFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contaPopupService
                    .open(ContaFinappDialogComponent as Component, params['id']);
            } else {
                this.contaPopupService
                    .open(ContaFinappDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
