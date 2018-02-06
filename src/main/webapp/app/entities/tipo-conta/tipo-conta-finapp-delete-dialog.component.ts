import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoContaFinapp } from './tipo-conta-finapp.model';
import { TipoContaFinappPopupService } from './tipo-conta-finapp-popup.service';
import { TipoContaFinappService } from './tipo-conta-finapp.service';

@Component({
    selector: 'jhi-tipo-conta-finapp-delete-dialog',
    templateUrl: './tipo-conta-finapp-delete-dialog.component.html'
})
export class TipoContaFinappDeleteDialogComponent {

    tipoConta: TipoContaFinapp;

    constructor(
        private tipoContaService: TipoContaFinappService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoContaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipoContaListModification',
                content: 'Deleted an tipoConta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-conta-finapp-delete-popup',
    template: ''
})
export class TipoContaFinappDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoContaPopupService: TipoContaFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tipoContaPopupService
                .open(TipoContaFinappDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
