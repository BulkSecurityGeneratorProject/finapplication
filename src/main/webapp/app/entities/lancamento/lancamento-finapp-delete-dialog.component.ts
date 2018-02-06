import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LancamentoFinapp } from './lancamento-finapp.model';
import { LancamentoFinappPopupService } from './lancamento-finapp-popup.service';
import { LancamentoFinappService } from './lancamento-finapp.service';

@Component({
    selector: 'jhi-lancamento-finapp-delete-dialog',
    templateUrl: './lancamento-finapp-delete-dialog.component.html'
})
export class LancamentoFinappDeleteDialogComponent {

    lancamento: LancamentoFinapp;

    constructor(
        private lancamentoService: LancamentoFinappService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lancamentoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lancamentoListModification',
                content: 'Deleted an lancamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lancamento-finapp-delete-popup',
    template: ''
})
export class LancamentoFinappDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lancamentoPopupService: LancamentoFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lancamentoPopupService
                .open(LancamentoFinappDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
