import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TipoContaFinapp } from './tipo-conta-finapp.model';
import { TipoContaFinappPopupService } from './tipo-conta-finapp-popup.service';
import { TipoContaFinappService } from './tipo-conta-finapp.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tipo-conta-finapp-dialog',
    templateUrl: './tipo-conta-finapp-dialog.component.html'
})
export class TipoContaFinappDialogComponent implements OnInit {

    tipoConta: TipoContaFinapp;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tipoContaService: TipoContaFinappService,
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
        if (this.tipoConta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipoContaService.update(this.tipoConta));
        } else {
            this.subscribeToSaveResponse(
                this.tipoContaService.create(this.tipoConta));
        }
    }

    private subscribeToSaveResponse(result: Observable<TipoContaFinapp>) {
        result.subscribe((res: TipoContaFinapp) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TipoContaFinapp) {
        this.eventManager.broadcast({ name: 'tipoContaListModification', content: 'OK'});
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
    selector: 'jhi-tipo-conta-finapp-popup',
    template: ''
})
export class TipoContaFinappPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoContaPopupService: TipoContaFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tipoContaPopupService
                    .open(TipoContaFinappDialogComponent as Component, params['id']);
            } else {
                this.tipoContaPopupService
                    .open(TipoContaFinappDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
