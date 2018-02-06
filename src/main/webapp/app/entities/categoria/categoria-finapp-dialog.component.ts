import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CategoriaFinapp } from './categoria-finapp.model';
import { CategoriaFinappPopupService } from './categoria-finapp-popup.service';
import { CategoriaFinappService } from './categoria-finapp.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-categoria-finapp-dialog',
    templateUrl: './categoria-finapp-dialog.component.html'
})
export class CategoriaFinappDialogComponent implements OnInit {

    categoria: CategoriaFinapp;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private categoriaService: CategoriaFinappService,
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
        if (this.categoria.id !== undefined) {
            this.subscribeToSaveResponse(
                this.categoriaService.update(this.categoria));
        } else {
            this.subscribeToSaveResponse(
                this.categoriaService.create(this.categoria));
        }
    }

    private subscribeToSaveResponse(result: Observable<CategoriaFinapp>) {
        result.subscribe((res: CategoriaFinapp) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CategoriaFinapp) {
        this.eventManager.broadcast({ name: 'categoriaListModification', content: 'OK'});
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
    selector: 'jhi-categoria-finapp-popup',
    template: ''
})
export class CategoriaFinappPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoriaPopupService: CategoriaFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.categoriaPopupService
                    .open(CategoriaFinappDialogComponent as Component, params['id']);
            } else {
                this.categoriaPopupService
                    .open(CategoriaFinappDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
