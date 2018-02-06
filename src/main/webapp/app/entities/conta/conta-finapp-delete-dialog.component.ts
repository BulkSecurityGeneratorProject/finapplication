import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContaFinapp } from './conta-finapp.model';
import { ContaFinappPopupService } from './conta-finapp-popup.service';
import { ContaFinappService } from './conta-finapp.service';

@Component({
    selector: 'jhi-conta-finapp-delete-dialog',
    templateUrl: './conta-finapp-delete-dialog.component.html'
})
export class ContaFinappDeleteDialogComponent {

    conta: ContaFinapp;

    constructor(
        private contaService: ContaFinappService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contaListModification',
                content: 'Deleted an conta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-conta-finapp-delete-popup',
    template: ''
})
export class ContaFinappDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contaPopupService: ContaFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contaPopupService
                .open(ContaFinappDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
