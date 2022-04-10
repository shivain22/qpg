import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { CategoryMasterUpdateComponent } from 'app/entities/category-master/category-master-update.component';
import { CategoryMasterService } from 'app/entities/category-master/category-master.service';
import { CategoryMaster } from 'app/shared/model/category-master.model';

describe('Component Tests', () => {
  describe('CategoryMaster Management Update Component', () => {
    let comp: CategoryMasterUpdateComponent;
    let fixture: ComponentFixture<CategoryMasterUpdateComponent>;
    let service: CategoryMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [CategoryMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoryMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryMaster(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryMaster();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
