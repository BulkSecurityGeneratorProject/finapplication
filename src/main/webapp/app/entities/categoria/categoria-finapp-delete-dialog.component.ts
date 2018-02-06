import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CategoriaFinapp } from './categoria-finapp.model';
import { CategoriaFinappPopupService } from './categoria-finapp-popup.service';
import { CategoriaFinappService } from './categoria-finapp.service';

@Component({
    selector: 'jhi-categoria-finapp-delete-dialog',
    templateUrl: './categoria-finapp-delete-dialog.component.html'
})
export class CategoriaFinappDeleteDialogComponent {

    categoria: CategoriaFinapp;

    constructor(
        private categoriaService: CategoriaFinappService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categoriaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'categoriaListModification',
                content: 'Deleted an categoria'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-categoria-finapp-delete-popup',
    template: ''
})
export class CategoriaFinappDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoriaPopupService: CategoriaFinappPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.categoriaPopupService
                .open(CategoriaFinappDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
