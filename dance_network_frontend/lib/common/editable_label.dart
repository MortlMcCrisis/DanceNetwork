import 'package:dance_network_frontend/theme.dart';
import 'package:flutter/material.dart';

class EditableLabel extends StatefulWidget {
  final String initialText;
  final String fallbackText;
  final ValueChanged<String> onSubmitted;
  final TextStyle? style;
  final bool active;

  const EditableLabel({
    super.key,
    required this.initialText,
    required this.fallbackText,
    required this.onSubmitted,
    required this.active,
    this.style,
  });

  @override
  State<EditableLabel> createState() => _EditableLabelState();
}

class _EditableLabelState extends State<EditableLabel> {
  bool _isEditing = false;
  bool _isHovering = false;
  late TextEditingController controller;
  late FocusNode focusNode;
  late String currentText;

  @override
  void initState() {
    super.initState();
    currentText = widget.initialText;
    controller = TextEditingController(text: currentText);
    focusNode = FocusNode();
    focusNode.addListener(_handleFocusChange);
  }

  @override
  void didUpdateWidget(covariant EditableLabel oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.initialText != widget.initialText) {
      currentText = widget.initialText;
      controller.text = currentText;
    }
  }

  @override
  void dispose() {
    controller.dispose();
    focusNode.removeListener(_handleFocusChange);
    focusNode.dispose();
    super.dispose();
  }

  void _handleFocusChange() {
    if (!focusNode.hasFocus && _isEditing) {
      setState(() {
        currentText = controller.text;
        _isEditing = false;
        _isHovering = false;
      });
      widget.onSubmitted(currentText);
    }
  }

  @override
  Widget build(BuildContext context) {
    if (_isEditing) {
      return TextField(
        controller: controller,
        focusNode: focusNode,
        autofocus: true,
        style: widget.style ?? Theme.of(context).textTheme.bodyMedium,
      );
    }

    Widget textField = Text(
      currentText != "" ? currentText : widget.fallbackText,
      style: widget.style?.copyWith(
          color: currentText != ""
              ? widget.style?.color
              : widget.style?.color?.withOpacity(0.25)) ??
          Theme.of(context).textTheme.bodyMedium?.copyWith(
            color: currentText != ""
                ? Theme.of(context).textTheme.bodyMedium?.color
                : Theme.of(context).textTheme.bodyMedium?.color?.withOpacity(0.25),
          ),
      softWrap: true,
    );

    if (!widget.active) {
      return textField;
    }

    return MouseRegion(
      onEnter: (_) => setState(() => { if(widget.active) _isHovering = true}),
      onExit: (_) => setState(() => _isHovering = false),
      child: GestureDetector(
        onTap: () {
          setState(() => { if(widget.active) _isEditing = true});
          Future.delayed(Duration.zero, () {
            focusNode.requestFocus();
          });
        },
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
          decoration: BoxDecoration(
            color: _isHovering ? AppThemes.generateGradient(AppThemes.black)[6].withOpacity(0.1) : Colors.transparent,
            borderRadius: BorderRadius.circular(4),
          ),
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              Flexible(child:
                textField,
              ),
              const SizedBox(width: 6),
              Icon(Icons.edit, size: 18, color: AppThemes.generateGradient(AppThemes.black)[7]),
            ],
          ),
        ),
      ),
    );
  }
}
